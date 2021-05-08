import AsyncStorage from '@react-native-async-storage/async-storage';
import { useNavigation } from '@react-navigation/core';
import React from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { Button } from 'react-native-paper';
import { list } from '../../data/Dev';

interface IProps {}

const UserSettings: React.FC<IProps> = () => {
  const navigation = useNavigation();

  return (
    <View style={styles.container}>
      <Button onPress={() => navigation.navigate('Login')}>Log out</Button>
      <Button onPress={() => navigation.navigate('ManagerTabs')}>Switch to Manager account</Button>
    </View>
  )
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
});

export default UserSettings;
