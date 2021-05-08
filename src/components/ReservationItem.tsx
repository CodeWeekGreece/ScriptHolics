import React, { useContext, useState } from 'react';
import { StyleSheet, Text, View, Image, Pressable } from 'react-native';
import { MaterialIcons } from '@expo/vector-icons';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { list } from '../data/Dev';

interface IProps {}

const ReservationItem: React.FC<IProps> = ({ data, value }) => {

  const removeItem = async(uuid: string) => {
    try {
      const resList = await AsyncStorage.getItem(list);
      const parsed = JSON.parse(resList!)
      const removed = parsed.filter((e) => e.uuid !== uuid)
      await AsyncStorage.setItem(list, JSON.stringify(removed))
    } catch (e) {
      console.error(e)
    }
    value.setRefresh(!value.refresh)
  }

  return (
    <View style={styles.container}>
      <View style={{ flexDirection: 'row' }}>
        <Image style={styles.image} source={{ uri: data.image }} />
        <View style={{flexDirection: 'column', justifyContent: 'space-between'}}>
          <Text style={styles.title}>{data.name}</Text>
          <View style={styles.infoConatiner}>
            <Text>Slot: {data.slot}</Text>
            <Text>{data.time}</Text>
          </View>
        </View>
      </View>
      <Pressable
        style={styles.clear}
        android_ripple={{ color: '#c0c0c0' }}
        onPress={() => removeItem(data.uuid)}
      >
        <MaterialIcons name="close" color="gray" size={22} />
      </Pressable>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'flex-start',
    justifyContent: 'space-between',
    flexDirection: 'row',
    paddingBottom: 15,
    marginLeft: 5,
    marginRight: 5,
  },
  image: {
    width: 90,
    height: 100,
    resizeMode: 'cover',
  },
  title: {
    marginLeft: 5,
    alignSelf: 'flex-start',
  },
  clear: {
    paddingLeft: 10,
    paddingRight: 10,
    paddingBottom: 10,
  },
  infoConatiner: {
    marginLeft: 5,
  }
});

export default ReservationItem;