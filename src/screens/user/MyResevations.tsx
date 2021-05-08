import AsyncStorage from '@react-native-async-storage/async-storage';
import { useFocusEffect } from '@react-navigation/core';
import React, { createContext, useContext, useEffect, useState } from 'react';
import { ScrollView, StyleSheet, Text, View } from 'react-native';
import { Button } from 'react-native-paper';
import ReservationItem from '../../components/ReservationItem';
import { list } from '../../data/Dev';

interface IProps {}

const MyResevations: React.FC<IProps> = () => {
  const [resList, setResList] = useState();
  const [empty, setEmpty] = useState(true);
  const [focused, setFocused] = useState(false);
  const [refresh, setRefresh] = useState(false);
  const value = {refresh, setRefresh}

  useEffect(() => {
    const getList = async() => {
      try {
        const reservations = await AsyncStorage.getItem(list)
        const res = JSON.parse(reservations);
        if (res === []) {
          setEmpty(true)
        } else {
          setResList(res);
        }
      } catch (e) {
        console.error(e)
      }
    }

    getList()
  }, [focused, refresh]);

  useFocusEffect(() => {
    setFocused(true);

    return () => {
      setFocused(false);
    }
  });

  return (
    <View style={styles.mainContainer}>
      {(resList === null || resList === undefined || resList === []) ? <Text>Empty</Text> : (
      <ScrollView>
          {resList.map((e, i) => <ReservationItem value={value} data={e} key={i} />)}
      </ScrollView>)}
    </View>
  )
};

const styles = StyleSheet.create({
  mainContainer: {
    alignItems: 'flex-start',
    flexDirection: 'row',
    marginTop: 10,
  },
});

export default MyResevations;
