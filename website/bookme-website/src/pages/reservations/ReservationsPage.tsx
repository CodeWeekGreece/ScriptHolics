import React, { useState } from 'react';
import { FirebaseDatabaseNode, FirebaseDatabaseProvider } from '@react-firebase/database';
import firebase from 'firebase/app';
import { Redirect } from 'react-router-dom';
import { CircularProgress, Container } from '@material-ui/core';
import NavBar from '../../components/NavBar/NavBar';
import { useAuth } from '../../contexts/AuthContext';
import firebaseConfig from '../../dev';
import 'firebase/database';
import SlotContainer from '../../components/SlotContainer/SlotContainer';
import './ReservationsPage.css';

function ReservationsPage(): JSX.Element {
  const { id, category } = useAuth();
  const [isNotLoggedIn] = useState<boolean>(id === '');

  return (
    <div className="root">
      <NavBar />
      <main className="content">
        <Container maxWidth="lg">
          <h1>Reservations</h1>
          <div className="slot-container">
            <FirebaseDatabaseProvider {...firebaseConfig} firebase={firebase}>
              <FirebaseDatabaseNode path={`/shops/${category}/${id}/slots`}>
                {(d) => {
                  if (d.value !== null) {
                    const slotArray = Object.keys(d.value).map((key) => d.value[key]);
                    const filteredSlots = slotArray.filter((key) => key.available === false);

                    return (
                      <>
                        {filteredSlots.map((slot) => {
                          return <SlotContainer key={slot.uid} slot={slot} owner={slot.owner} />;
                        })}
                      </>
                    );
                  }
                  return <CircularProgress className="spinner" />;
                }}
              </FirebaseDatabaseNode>
            </FirebaseDatabaseProvider>
          </div>
        </Container>
      </main>
      {isNotLoggedIn ? <Redirect to="/" /> : null}
    </div>
  );
}

export default ReservationsPage;
