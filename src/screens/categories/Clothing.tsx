import React from 'react';
import { ScrollView, StyleSheet, Text, View } from 'react-native';
import Item from '../../components/Item';
import { clothing } from '../../data/MockShops';

interface IProps {}

const Clothing: React.FC<IProps> = () => {
  return (
    <View style={styles.container}>
      <ScrollView>
        {clothing.map((c, i) => <Item data={c} key={i} />)}
      </ScrollView>
    </View>
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

export default Clothing;
