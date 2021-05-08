import { useNavigation } from '@react-navigation/core';
import React, { useState } from 'react';
import { Dimensions, StyleSheet, Text, View } from 'react-native';
import { Button, TextInput } from 'react-native-paper';

interface IProps {}

const UserSignup: React.FC<IProps> = () => {
  const [fName, setFName] = useState('');
  const [lName, setLName] = useState('');
  const [email, setEmail] = useState('');
  const [phoneNum, setPhoneNum] = useState('');
  const [password, setPassword] = useState('');
  const [conPassword, setConPassword] = useState('');
  const [isSafe, setIsSafe] = useState(true);

  const navigation = useNavigation();

  const handleConfirmation = () => {
    if (password !== conPassword) {
      setIsSafe(false)
    } else setIsSafe(true)
  }

  return (
    <View style={styles.container}>
      <TextInput
        style={{ width: 160, marginRight: 5 }}
        label="First name"
        value={fName}
        mode="outlined"
        autoCompleteType="name"
        onChangeText={(e) => setFName(e)}
      />
      <TextInput
        style={{ width: 160, marginLeft: 5 }}
        label="Last name"
        value={lName}
        autoCompleteType="name"
        onChangeText={(e) => setLName(e)}
        mode="outlined"
      />
      <TextInput
        style={{ width: 340 }}
        label="Email"
        mode="outlined"
        value={email}
        autoCompleteType="email"
        onChangeText={(e) => setEmail(e)}
      />
      <TextInput
        style={{ width: 160, marginRight: 5 }}
        label="Password"
        secureTextEntry
        value={password}
        mode="outlined"
        onChangeText={(e) => setPassword(e)}
      />
      <TextInput
        style={{ width: 160, marginLeft: 5 }}
        label="Confirm Password"
        error={!isSafe}
        secureTextEntry
        value={conPassword}
        onChangeText={(e) => setConPassword(e)}
        mode="outlined"
        onBlur={() => handleConfirmation()}
      />
      <TextInput
        style={{ width: 340 }}
        label="Phone Number"
        mode="outlined"
        value={phoneNum}
        onChangeText={(e) => setPhoneNum(e)}
      />
      <Button
        mode="contained"
        style={{ marginTop: 20, width: 340 }}
        onPress={() => navigation.navigate('UserTabs')}
      >
        Sign up
      </Button>
    </View>
  )
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'space-evenly',
    flexDirection: 'row',
    flexWrap: 'wrap',
    marginTop: 100
  },
});

export default UserSignup;
