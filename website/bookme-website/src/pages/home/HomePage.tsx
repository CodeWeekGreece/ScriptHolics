import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { Redirect } from 'react-router-dom';
import { CircularProgress, Container } from '@material-ui/core';
import NavBar from '../../components/NavBar/NavBar';
import config from '../../dev';
import { useAuth } from '../../contexts/AuthContext';
import Shop from '../../components/Shop/Shop';
import './HomePage.css';

function HomePage(): JSX.Element {
  const [shop, setShop] = useState(null);
  const { id, category } = useAuth();
  const [isNotLoggedIn] = useState<boolean>(id === '');

  useEffect(() => {
    axios
      .get(`${config.databaseURL}/shops/${category}/${id}.json`)
      .then((res) => {
        setShop(res.data);
      })
      .catch();
  }, []);

  return (
    <div className="root">
      <NavBar />
      <main className={shop !== null ? 'content' : 'loading'}>
        <Container maxWidth="lg">
          <div>{shop !== null ? <Shop shop={shop!} /> : <CircularProgress className="spinner" />}</div>
        </Container>
      </main>
      {isNotLoggedIn ? <Redirect to="/" /> : null}
    </div>
  );
}

export default HomePage;
