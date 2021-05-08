import React from 'react';
import { StyleSheet, Text, View } from 'react-native';

interface IProps {}

const ManagerHome: React.FC<IProps> = () => (
  <View style={styles.container}>
    <Text>ManagerHome</Text>
  </View>
);

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
});

export default ManagerHome;
