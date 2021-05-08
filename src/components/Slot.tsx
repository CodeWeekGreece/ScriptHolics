import React, { useState } from 'react';
import { Pressable, StyleSheet, Text, View } from 'react-native';
import DateTimePicker from '@react-native-community/datetimepicker';

interface IProps {}

const Slot: React.FC<IProps> = ({index, data}) => {
  return (
    <View style={styles.container}>
      <Text>Slot {index}: </Text>
      <Text>{data.startTime}</Text>
      <Text>{data.endTime}</Text>
    </View>
  )
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    // alignItems: 'center',
    justifyContent: 'space-between',
    flexDirection: 'row',
    marginBottom: 15,
  },
});

export default Slot;
