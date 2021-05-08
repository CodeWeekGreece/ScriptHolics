import AsyncStorage from '@react-native-async-storage/async-storage';
import { Picker } from '@react-native-picker/picker';
import { useRoute } from '@react-navigation/core';
import React, { useState } from 'react';
import { Dimensions, Image, ScrollView, StyleSheet, Text, View } from 'react-native';
import { Button } from 'react-native-paper';
import { AirbnbRating } from 'react-native-ratings';
import { list } from '../../data/Dev';
import Toast from 'react-native-simple-toast';
import { v4 as uuidv4 } from 'uuid';
import { slots } from '../../data/Mock';
import Slot from '../../components/Slot';

interface IProps {}

const Reserve: React.FC<IProps> = () => {
  const route = useRoute();
  const params = route.params;
  const [slotNumber, setSlotNumber] = useState(1);
  const [loading, setLoading] = useState(false);

  const saveReservation = async () => {
    setLoading(true);
    setTimeout(() => {
      console.log('Go')
    }, 2000);
    try {
      // await AsyncStorage.getItem(list)
      //   .then((reservations) => {
      //     const res: Array<object> = JSON.parse(reservations);
      //     res.push({
      //       name: params.name,
      //       image: params.image,
      //       date: selectedDate,
      //       time: selectedTime,
      //       uuid: uuidv4(),
      //     });
      //     AsyncStorage.setItem(list, JSON.stringify(res));
      //   });
      setLoading(false);
      Toast.show('Your reservation was a success!', Toast.LONG)
    } catch (e) {
      console.error(e)
    }
  }

  return (
    <ScrollView>
      <Image source={{uri: params.image}} style={styles.imageContainer} />
      <View style={styles.titleContainer}>
        <Text style={styles.title}>{params.name}</Text>
        <AirbnbRating starContainerStyle={{marginTop: 2, marginRight: 10,}} showRating={false} size={20} isDisabled />
      </View>
      <Text style={styles.descirption}>
        {params.description}
      </Text>
      <Text style={styles.price}>Price:
        <Text style={{ color: 'green', fontWeight: 'bold' }}> {params.price}</Text>
      </Text>
      <Text style={{marginLeft: 10}}>Open hours:
        <Text style={{fontWeight: 'bold'}}> {params.hours}</Text>
      </Text>
      <View style={styles.reservationContainer}>
      <View style={styles.slotContainer}>
        <Text style={{ fontWeight: 'bold', fontSize: 17 }}>Slots</Text>
        <Text style={{ marginTop: 10, fontSize: 15 }}>Choose a slot to reserve: </Text>
        <Picker
        style={{ width: 90, marginBottom: 10 }}
        selectedValue={slotNumber}
        onValueChange={(itemValue, itemIndex) => setSlotNumber(itemValue)}>
          <Picker.Item label="1" value={1} />
          {/* <Picker.Item label="2" value={2} /> */}
          <Picker.Item label="3" value={3} />
        </Picker>
        {slots.map((e, i) => <Slot key={i} index={i + 1} data={e} />)}
        </View>
      </View>
      <Button
        loading={loading}
        onPress={saveReservation}
        mode="contained"
        style={styles.button}>
          Reserve
      </Button>
    </ScrollView>
  )
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  imageContainer: {
    // flex: 1, 
    resizeMode: 'cover',
    width: Dimensions.get('window').width,
    height: 200,
  },
  titleContainer: {
    flex: 1,
    marginTop: 10,
    marginLeft: 10,
    flexDirection: 'row',
    justifyContent: 'space-between'
  },
  title: {
    fontSize: 20
  },
  descirption: {
    marginTop: 15,
    marginLeft: 10,
    marginRight: 10
  },
  price: {
    marginLeft: 10,
    marginTop: 10
  },
  reservationContainer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginLeft: 5,
    marginRight: 10,
    flexWrap: 'wrap'
  },
  button: {
    marginTop: 10,
    marginBottom: 10,
    marginLeft: 50,
    marginRight: 50,
  },
  slotContainer: {
    marginTop: 20,
    marginLeft: 10,
    marginRight: 15,
  },
});

export default Reserve;
