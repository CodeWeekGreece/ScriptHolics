import React from 'react';
import { BrowserRouter, Route } from 'react-router-dom';
import firebase from 'firebase/app';
import 'firebase/auth';
import { FirebaseAuthProvider } from '@react-firebase/auth';
import LoginPage from './pages/auth/login/LoginPage';
import firebaseConfig from './dev';
import HomePage from './pages/home/HomePage';
import ReservationsPage from './pages/reservations/ReservationsPage';
import SettingsPage from './pages/settings/SettingsPage';
import { AuthContextProvider } from './contexts/AuthContext';
import './App.css';
import RegisterPage from './pages/auth/register/RegisterPage';
import ShopCreationPage from './pages/shopCreation/ShopCreationPage';

function App(): JSX.Element {
  return (
    <BrowserRouter>
      <div className="App">
        <FirebaseAuthProvider firebase={firebase} {...firebaseConfig}>
          <AuthContextProvider>
            <Route exact path="/" component={LoginPage} />
            <Route exact path="/register" component={RegisterPage} />
            <Route exact path="/shopCreation" component={ShopCreationPage} />
            <Route exact path="/home" component={HomePage} />
            <Route exact path="/reservations" component={ReservationsPage} />
            <Route exact path="/settings" component={SettingsPage} />
          </AuthContextProvider>
        </FirebaseAuthProvider>
      </div>
    </BrowserRouter>
  );
}

export default App;
