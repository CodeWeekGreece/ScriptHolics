import React, { useState } from 'react';
import { Pressable, StyleSheet, Text, View } from 'react-native';

interface IProps {}

const Slot: React.FC<IProps> = ({index, data}) => {
  return (
    <View style={styles.container}>

      {data.available
      ? (
        <>
          <Text>Slot {index}:       {data.startTime} {data.endTime}</Text>
        </>
      )
      : (
        <>
          <Text style={styles.unavailable}>Slot {index}:       {data.startTime} {data.endTime}</Text>
        </>
      )}
    </View>
  )
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'space-between',
    flexDirection: 'row',
    marginBottom: 15,
  },
  unavailable: {
    color: 'gray',
    textDecorationLine: 'line-through'
  }
});

export default Slot;
