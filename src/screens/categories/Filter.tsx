import { Picker } from '@react-native-picker/picker';
import React, { useState } from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { AirbnbRating } from 'react-native-ratings';

interface IProps {}

const Filter: React.FC<IProps> = () => {
  const [city, setCity] = useState('Heraklion');
  const [distance, setDistance] = useState('Within 1km');

  return (
    <View style={styles.mainContainer}>
      <View style={styles.itemContainer}>
        <Text style={styles.text}>Location: </Text>
        <Picker
          style={{ width: 170 }}
          selectedValue={city}
          onValueChange={(city) => setCity(city)}
        >
          <Picker.Item label="Heraklion" value="heraklion" />
          <Picker.Item label="Athens" value="athens" />
          <Picker.Item label="Thessaloniki" value="thessaloniki" />
          <Picker.Item label="Patras" value="patras" />
        </Picker>
      </View>
      <View style={styles.itemContainer}>
        <Text style={styles.text}>Rating:</Text>
        <AirbnbRating
          size={25}
          starContainerStyle={{ marginTop: 10, marginRight: 13 }}
          defaultRating={1}

          count={5}
          showRating={false}
        />
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
  },
  text: {
    fontSize: 17,
    marginTop: 12,
  }
});

export default Filter;
