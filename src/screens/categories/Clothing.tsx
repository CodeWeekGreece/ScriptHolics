import React from 'react';
import { ScrollView, StyleSheet, Text, View } from 'react-native';
import { FAB } from 'react-native-paper';
import FABFilter from '../../components/FABFilter';
import Item from '../../components/Item';
import { clothing } from '../../data/Mock';


interface IProps {}

const Clothing: React.FC<IProps> = () => {
  return (
    <>
      <View style={styles.container}>
        <ScrollView>
          {clothing.map((c, i) => <Item data={c} key={i} />)}
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

export default Clothing;
