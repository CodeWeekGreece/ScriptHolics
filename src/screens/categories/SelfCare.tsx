import React from 'react';
import { ScrollView, StyleSheet, Text, View } from 'react-native';
import FABFilter from '../../components/FABFilter';
import Item from '../../components/Item';
import { selfCare } from '../../data/Mock';

interface IProps {}

const SelfCare: React.FC<IProps> = () => {
  return (
    <>
      <View style={styles.container}>
        <ScrollView>
          {selfCare.map((s, i) => <Item data={s} key={i} />)}
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
    justifyContent: 'center',
  },
});

export default SelfCare;
