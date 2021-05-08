import { NavigationActions } from '@react-navigation/compat';
import { useNavigation } from '@react-navigation/native';
import React from 'react';
import { Dimensions, ImageBackground, StyleSheet, Text, View } from 'react-native';
import { Button } from 'react-native-paper';

interface IProps {}

const Login: React.FC<IProps> = () => {
  const navigation = useNavigation();

  return (
      <ImageBackground style={styles.container} source={require('../../assets/bg.png')}>
        <Button onPress={() => navigation.navigate('UserTabs')}>Log in as user</Button>
        <Button onPress={() => navigation.navigate('ManagerTabs')}>Log in as manager</Button>
      </ImageBackground>
)};

const styles = StyleSheet.create({
  container: {
    flex: 1, 
    resizeMode: 'cover'
  },
});

export default Login;
