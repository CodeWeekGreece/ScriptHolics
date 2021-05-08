import React from 'react';
import { ScrollView, StyleSheet, Text, View } from 'react-native';
import Item from '../../components/Item';
import { selfCare } from '../../data/MockShops';

interface IProps {}

const SelfCare: React.FC<IProps> = () => {
  return (
    <View style={styles.container}>
      <ScrollView>
        {selfCare.map((s, i) => <Item data={s} key={i} />)}
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

export default SelfCare;
