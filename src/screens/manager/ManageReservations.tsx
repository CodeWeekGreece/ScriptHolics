import React from 'react';
import { StyleSheet, Text, View } from 'react-native';
import TicketItem from '../../components/TicketItem';
import { reservations } from '../../data/MockShops';

interface IProps {}

const ManageReservations: React.FC<IProps> = () => {
  

  return (
    <View style={styles.container}>
      {reservations.map((e, i) => <TicketItem key={i} data={e} />)}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    // alignItems: 'center',
    // justifyContent: 'center',
  },
});

export default ManageReservations;
