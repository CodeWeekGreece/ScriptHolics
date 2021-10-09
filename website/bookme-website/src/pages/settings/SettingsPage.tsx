import { Container } from '@material-ui/core';
import React, { useState, useEffect } from 'react';

import { Redirect } from 'react-router-dom';
import NavBar from '../../components/NavBar/NavBar';
import { useAuth } from '../../contexts/AuthContext';

function SettingsPage(): JSX.Element {
  const { id, logout } = useAuth();
  const [isNotLoggedIn, setIsNotLoggedIn] = useState<boolean>(id === '');

  useEffect(() => {
    if (id === '') {
      setIsNotLoggedIn(true);
    }
  }, [id]);

  return (
    <div className="root">
      <NavBar />
      <main className="content">
        <Container maxWidth="lg">
          <h1>Settings</h1>
          <button type="button" onClick={logout}>
            Log Out
          </button>
        </Container>
      </main>
      {isNotLoggedIn ? <Redirect to="/" /> : null}
    </div>
  );
}

export default SettingsPage;
