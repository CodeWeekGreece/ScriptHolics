import { NavigationActions } from '@react-navigation/compat';
import { useNavigation } from '@react-navigation/native';
import React from 'react';
import { Dimensions, ImageBackground, StyleSheet, Text, View } from 'react-native';
import { Button } from 'react-native-paper';

interface IProps {}

const Login: React.FC<IProps> = () => {
  const navigation = useNavigation();

  return (
    <View style={styles.mainContainer}>
      {/* <ImageBackground style={styles.container} source={require('../../assets/bg.png')}> */}
      <Button onPress={() => navigation.navigate('User Login')}>Log in as user</Button>
      <Button onPress={() => navigation.navigate('Manager Login')}>Log in as manager</Button>
      {/* </ImageBackground> */}
    </View>
)};

const styles = StyleSheet.create({
  container: {
    flex: 1, 
    resizeMode: 'cover'
  },
  mainContainer: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  }
});

export default Login;
