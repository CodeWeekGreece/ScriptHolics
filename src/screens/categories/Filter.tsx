import { Picker } from '@react-native-picker/picker';
import React, { useState } from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { AirbnbRating } from 'react-native-ratings';

interface IProps {}

const Filter: React.FC<IProps> = () => {
  const [city, setCity] = useState('Heraklion');
  const [price, setPrice] = useState('$');

  return (
    <View style={styles.mainContainer}>
      <View style={styles.itemContainer}>
        <Text style={styles.text}>Location: </Text>
        <Picker
          style={{ width: 170 }}
          selectedValue={city}
          onValueChange={(city) => setCity(city)}
          mode="dialog"
        >
          <Picker.Item label="Heraklion" value="heraklion" />
          <Picker.Item label="Athens" value="athens" />
          <Picker.Item label="Thessaloniki" value="thessaloniki" />
          <Picker.Item label="Patras" value="patras" />
        </Picker>
      </View>
      <View style={{...styles.itemContainer, marginBottom: 20}}>
        <Text style={styles.text}>Rating:</Text>
        <AirbnbRating
          size={25}
          starContainerStyle={{ marginTop: 10, marginRight: 13 }}
          defaultRating={1}

          count={5}
          showRating={false}
        />
      </View>
      <View style={styles.itemContainer}>
        <Text style={styles.text}>Price:</Text>
        <Picker
          style={{ width: 170 }}
          selectedValue={price}
          onValueChange={(pr) => setPrice(pr)}
          mode="dropdown"
        >
          <Picker.Item label="$" value={1} />
          <Picker.Item label="$$" value={2} />
          <Picker.Item label="$$$" value={3} />
          <Picker.Item label="$$$" value={4} />
        </Picker>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  mainContainer: {
    flex: 1,
    marginLeft: 10,
    marginRight: 10
  },
  itemContainer: {
    justifyContent: 'space-between',
    flexDirection: 'row',
    marginBottom: 10,
  },
  text: {
    fontSize: 17,
    marginTop: 12,
  }
});

export default Filter;
