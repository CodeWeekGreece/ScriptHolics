import React, { useEffect, useState } from 'react';
import { TextInput as Input, Dimensions, Image, Pressable, ScrollView, StyleSheet, Text, View } from 'react-native';
import { MaterialIcons } from '@expo/vector-icons'
import { restaurants, slots } from '../../data/MockShops';
import { TextInput } from 'react-native-paper';
import InputDialog from '../../components/InputDialog';
import { Picker } from '@react-native-picker/picker';
import Slot from '../../components/Slot';

interface IProps {}

const ManagerHome: React.FC<IProps> = () => {
  const [title, setTitle] = useState(restaurants[1].name);
  const [description, setDescription] = useState(restaurants[1].description);
  const [changingTitle, setChangingTitle] = useState(false)
  const [changingDesc, setChangingDesc] = useState(false);
  const [slotNumber, setSlotNumber] = useState(3);

  return (
    <View style={styles.container}>
      <ScrollView>
        <Image source={{ uri: restaurants[1].image }} style={styles.image} />
        <View style={styles.titleContainer}>
          <TextInput
            label="Title"
            style={{ fontSize: 20 }}
            disabled={!changingTitle}
            value={title}
            onChangeText={value => setTitle(value)}
            mode="outlined"
          />
          <Pressable onPress={() => setChangingTitle(!changingTitle)}>
          {changingTitle
          ? <MaterialIcons
              style={{ marginLeft: 15, marginTop: 22 }}
              name="done"
              color="gray"
              size={24}
            />
          : <MaterialIcons
              style={{ marginLeft: 15, marginTop: 22 }}
              name="edit"
              color="gray"
              size={24}
            />}
          </Pressable>
        </View>
        <View style={styles.descriptionContainer}>
          <View style={{flexDirection: 'row'}}>
            <Text style={{ fontWeight: 'bold', fontSize: 17 }}>Description</Text>
            <Pressable onPress={() => setChangingDesc(!changingDesc)}>
              {changingDesc
              ? <MaterialIcons
                  style={{ marginLeft: 10, marginTop: 3 }}
                  name="done"
                  color="gray"
                  size={20}
                />
              : <MaterialIcons
                  style={{ marginLeft: 10, marginTop: 3 }}
                  name="edit"
                  color="gray"
                  size={20}
                />}
            </Pressable>
          </View>
          <TextInput
            style={{ marginTop: 5, }}
            disabled={!changingDesc}
            mode="outlined"
            multiline
            value={description}
            onChangeText={value => setDescription(value)}
          />
        </View>
        <View style={styles.slotContainer}>
          <Text style={{ fontWeight: 'bold', fontSize: 17 }}>Slots</Text>
          <Text style={{ marginTop: 10, fontSize: 15 }}>Number of slots: </Text>
          <Picker
          style={{ width: 90, marginBottom: 10 }}
          selectedValue={slotNumber}
          onValueChange={(itemValue, itemIndex) => setSlotNumber(itemValue)}>
            <Picker.Item label="1" value={1} />
            <Picker.Item label="2" value={2} />
            <Picker.Item label="3" value={3} />
            <Picker.Item label="4" value={4} />
            <Picker.Item label="5" value={5} />
            <Picker.Item label="6" value={6} />
            <Picker.Item label="7" value={7} />
            <Picker.Item label="8" value={8} />
            <Picker.Item label="9" value={9} />
            <Picker.Item label="10" value={10} />
        </Picker>
        {slots.map((e, i) => <Slot key={i} index={i + 1} data={e} />)}
        </View>
      </ScrollView>
    </View>
  )
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'flex-start',
    alignItems: 'flex-start',
  },
  image: { 
    resizeMode: 'cover',
    width: Dimensions.get('window').width,
    height: 200,
  },
  titleContainer: {
    flexDirection: 'row',
    marginTop: 5,
    marginLeft: 10,
  },
  title: {
    fontSize: 25,
  },
  descriptionContainer: {
    marginTop: 20,
    marginLeft: 10,
    marginRight: 15,
  },
  slotContainer: {
    marginTop: 20,
    marginLeft: 10,
    marginRight: 15,
  }
});

export default ManagerHome;
