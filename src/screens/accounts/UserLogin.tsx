import { useNavigation } from '@react-navigation/core';
import React, { useState } from 'react';
import { Pressable, StyleSheet, Text, View } from 'react-native';
import { Button, Colors, TextInput } from 'react-native-paper';

interface IProps {}

const UserLogin: React.FC<IProps> = () => {
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
        style={{ marginBottom: 20 }}
      />
      <Button
        onPress={() => navigation.navigate('UserTabs')}
        mode="contained"
      >
        Login
      </Button>
      <Pressable
        style={{ marginTop: 20, alignSelf: 'center' }}
        onPress={() => navigation.navigate('User Signup')}
      >
        <Text>
          Don't have an account?
          <Text style={{ color: Colors.purple900 }}> Sign up!</Text>
        </Text>
      </Pressable>
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
  }
});

export default UserLogin;
