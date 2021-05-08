import { useRoute } from '@react-navigation/core';
import React from 'react';
import { ScrollView, StyleSheet, Text, View } from 'react-native';
import FABFilter from '../../components/FABFilter';
import Item from '../../components/Item';
import { restaurants } from '../../data/Mock';

interface IProps {}

const Restaurants: React.FC<IProps> = () => {
  return (
    <>
      <View style={styles.container}>
        <ScrollView>
          {restaurants.map((r, i) => <Item data={r} key={i} />)}
        </ScrollView>
      </View>
      <FABFilter />
    </>
  )
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    marginTop: 10,
    // alignItems: 'center',
    justifyContent: 'center',
  },
});

export default Restaurants;
