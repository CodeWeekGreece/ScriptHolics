import { useNavigation } from '@react-navigation/native';
import React from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { Button } from 'react-native-paper';

interface IProps {}

const ManagerSettings: React.FC<IProps> = () => {
  const navigation = useNavigation();
  
  return (<View style={styles.container}>
    <Text>ManagerSettings</Text>
    <Button onPress={() => navigation.navigate('Login')}>Log out</Button>
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

export default ManagerSettings;
