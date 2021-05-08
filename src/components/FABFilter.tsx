import React from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { FAB } from 'react-native-paper';
import { MaterialIcons } from '@expo/vector-icons';
import { useNavigation } from '@react-navigation/core';

interface IProps {}

const FABFilter: React.FC<IProps> = () => {
  const navigation = useNavigation();

  return (
    <FAB
      small={false}
      style={styles.fab}
      color="black"
      icon={props => <MaterialIcons name="filter-list" {...props} />}
      onPress={() => navigation.navigate('Filter')}
    />
  )
};

const styles = StyleSheet.create({
  fab: {
    position: 'absolute',
    margin: 22,
    right: 0,
    bottom: 0,
    backgroundColor: 'white'
  }
});

export default FABFilter;
