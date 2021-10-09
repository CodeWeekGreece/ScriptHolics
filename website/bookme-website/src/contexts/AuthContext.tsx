import React, { createContext, useContext, useState, useEffect } from 'react';
import axios from 'axios';
import firebase from 'firebase/app';
import { loadFromStorage, saveToStorage, deleteFromStorage } from '../util/storage';
import config from '../dev';

const AuthContext = createContext<IContext>({} as IContext);

function AuthContextProvider({ children }: IProps): JSX.Element {
  const [id, setId] = useState('');
  const [category, setCategory] = useState('');
  const [result, setResult] = useState<[number, string] | null>(null);

  useEffect(() => {
    const user = loadFromStorage('USER');
    if (user === undefined) return;
    setId(user.id);
    setCategory(user.category);
    setResult([1, 'Success']);
  }, []);

  const registerUser = (email: string, password: string, phone: string, cat: string) => {
    firebase
      .auth()
      .createUserWithEmailAndPassword(email, password)
      .then(() => {
        const uid: string | undefined = firebase.auth().currentUser?.uid;

        if (uid !== undefined) {
          setId(uid);
          setCategory(cat);
          saveToStorage('USER', { id: uid, category: cat });
          axios.put(`${config.databaseURL}/users/${uid}.json`, {
            email,
            category: cat,
            phone,
            userType: 'owner',
          });
          setResult([1, 'Success']);
        }
      });
  };

  const login = (email: string, password: string) => {
    firebase
      .auth()
      .signInWithEmailAndPassword(email, password)
      .then(() => {
        const uid: string | undefined = firebase.auth().currentUser?.uid;

        if (uid !== undefined) {
          axios
            .get(`${config.databaseURL}/users/${uid}.json`)
            .then((res) => {
              if (res.data.userType !== 'owner') {
                firebase.auth().signOut();
                setResult([0, 'Access the app instead']);
              } else {
                setCategory(res.data.category);
                setId(uid);
                saveToStorage('USER', { id: uid, category: res.data.category });
                setResult([1, 'Success']);
              }
            })
            .catch((e) => {
              setResult([0, e]);
            });
        } else {
          setResult([0, 'ERROR']);
        }
      })
      .catch((e) => {
        setResult([0, `Error: ${e}`]);
      });
  };

  const logout = () => {
    firebase.auth().signOut();
    deleteFromStorage('USER');
    setResult(null);
    setId('');
  };

  return (
    <AuthContext.Provider value={{ id, category, registerUser, login, result, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

type IProps = {
  children: React.ReactNode;
};

type IContext = {
  id: string;
  category: string;
  registerUser: (email: string, password: string, phone: string, cat: string) => void;
  login: (email: string, password: string) => void;
  result: [number, string] | null;
  logout: () => void;
};

const useAuth = (): IContext => useContext(AuthContext);

export { AuthContextProvider, useAuth };
