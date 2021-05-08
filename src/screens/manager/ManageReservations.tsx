import React from 'react';
import { StyleSheet, Text, View } from 'react-native';

interface IProps {}

const ManageReservations: React.FC<IProps> = () => {
  return (
    <View style={styles.container}>
      <Text>Hi</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
});

export default ManageReservations;
