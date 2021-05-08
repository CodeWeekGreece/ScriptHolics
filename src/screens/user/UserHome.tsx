import AsyncStorage from '@react-native-async-storage/async-storage';
import { Picker } from '@react-native-picker/picker';
import { useFocusEffect, useNavigation } from '@react-navigation/native';
import React, { useEffect, useState } from 'react';
import { StyleSheet, Text, ScrollView, FlatList, View, BackHandler, ToastAndroid } from 'react-native';
import { Appbar } from 'react-native-paper';
import CategoryItem from '../../components/CategoryItem';
import Item from '../../components/Item';
import { list } from '../../data/Dev';
import { categories } from '../../data/Mock';

interface IProps {}

const UserHome: React.FC<IProps> = () => {
  const navigation = useNavigation();

  useFocusEffect(() => {
    const checkForStorage = async() => {
      const storage = await AsyncStorage.getItem(list)
      if (storage === null) {
         await AsyncStorage.setItem(list, JSON.stringify([]))
      }
    }

    checkForStorage();
  })

  return (
    <View style={styles.mainContainer}>
      <ScrollView>
        {categories.map((e, i) => <CategoryItem data={e} key={i} />)}
      </ScrollView>
    </View>
  )
};

const styles = StyleSheet.create({
  mainContainer: {
    // flex: 1,
    alignItems: 'flex-start',
    flexDirection: 'row',
    // flexWrap: 'nowrap',
    // justifyContent: 'center',
    marginTop: 10,
  },
});

export default UserHome;
