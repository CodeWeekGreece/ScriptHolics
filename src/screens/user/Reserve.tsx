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

interface IProps {}

const Reserve: React.FC<IProps> = () => {
  const route = useRoute();
  const params = route.params;
  const [selectedTime, setSelectedTime] = useState();
  const [selectedDate, setSelectedDate] = useState();
  const [loading, setLoading] = useState(false);

  const saveReservation = async () => {
    setLoading(true);
    try {
      await AsyncStorage.getItem(list)
        .then((reservations) => {
          const res: Array<object> = JSON.parse(reservations);
          res.push({
            name: params.name,
            image: params.image,
            date: selectedDate,
            time: selectedTime,
            uuid: uuidv4(),
          });
          AsyncStorage.setItem(list, JSON.stringify(res));
        });
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
        {/* Here will be the place description */}
        Lorem ipsum dolor sit amet consectetur, 
        adipisicing elit. Veritatis ab ea ex! Dolore eveniet 
        facere et laborum cumque sapiente aliquid optio, 
        velit, expedita itaque est. Reprehenderit quas ab nisi dolor.
      </Text>
      <Text style={styles.price}>Price:
        <Text style={{ color: 'green', fontWeight: 'bold' }}> {params.price}</Text>
      </Text>
      <Text style={{marginLeft: 10}}>Open hours:
        <Text style={{fontWeight: 'bold'}}> {params.hours}</Text>
      </Text>
      <View style={styles.reservationContainer}>
        <Picker
          style={{width: 170}}
          selectedValue={selectedDate}
          onValueChange={(itemValue, itemIndex) => setSelectedDate(itemValue)}>
            <Picker.Item label="10 May" value="10 May" />
            <Picker.Item label="11 May" value="11 May" />
            <Picker.Item label="12 May" value="12 May" />
            <Picker.Item label="13 May" value="13 May" />
        </Picker>
        <Picker
          style={{width: 170}}
          selectedValue={selectedTime}
          onValueChange={(itemValue, itemIndex) => setSelectedTime(itemValue)}>
            <Picker.Item label="10:30-11:00" value="10:30-11:00" />
            <Picker.Item label="14:15-14:45" value="14:15-14:45" />
        </Picker>
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
    // alignItems: 'center',
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
  }
});

export default Reserve;
