import React from 'react';
import { ScrollView, StyleSheet, Text, View } from 'react-native';
import Item from '../../components/Item';
import { supermarkets } from '../../data/MockShops';

interface IProps {}

const Supermarkets: React.FC<IProps> = () => (
  <View style={styles.container}>
      <ScrollView>
        {supermarkets.map((r, i) => <Item data={r} key={i} />)}
      </ScrollView>
    </View>
);

const styles = StyleSheet.create({
  container: {
    flex: 1,
    marginTop: 10,
    // alignItems: 'center',
    justifyContent: 'center',
  },
});

export default Supermarkets;
