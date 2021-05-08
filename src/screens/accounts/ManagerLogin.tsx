import { useNavigation } from '@react-navigation/core';
import React, { useState } from 'react';
import { Pressable, StyleSheet, Text, View } from 'react-native';
import { Button, TextInput } from 'react-native-paper';

interface IProps {}

const ManagerLogin: React.FC<IProps> = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigation = useNavigation()

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Welcome back!</Text>
      <TextInput
        label="Email"
        value={email}
        onChange={(email: string) => setEmail(email)}
        style={{ marginBottom: 20, }}
      />
      <TextInput
        label="Password"
        secureTextEntry
        value={password}
        onChange={(password: string) => setPassword(password)}
        style={{ marginBottom: 20, }}
      />
      <Button
        onPress={() => navigation.navigate('ManagerTabs')}
        mode="contained"
      >
        Login
      </Button>
    </View>
)};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    // alignItems: 'center',
    justifyContent: 'center',
    marginLeft: 20,
    marginRight: 20,
  },
  title: {
    fontSize: 25,
    alignSelf: 'center',
    marginBottom: 20,
  },
  signup: {
    alignSelf: 'center',
    marginTop: 15,
  }
});

export default ManagerLogin;
