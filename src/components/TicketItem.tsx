import React from 'react';
import { Pressable, StyleSheet, Text, View } from 'react-native';
import { MaterialIcons } from '@expo/vector-icons'

interface IProps {}

const TicketItem: React.FC<IProps> = ({ data }) => (
  <View style={styles.container}>
      <View style={{ flexDirection: 'row' }}>
        <View style={{flexDirection: 'column', justifyContent: 'space-between'}}>
          <View style={styles.infoConatiner}>
            <Text>{data.name}</Text>
            <Text>{data.phoneNum}</Text>
            <Text>Slot: {data.slot}</Text>
            <Text>{data.time}</Text>
          </View>
        </View>
      </View>
      <Pressable
        style={styles.clear}
        android_ripple={{ color: '#c0c0c0' }}
        // onPress={() => removeItem(data.uuid)}
      >
        <MaterialIcons name="close" color="gray" size={22} />
      </Pressable>
    </View>
);

const styles = StyleSheet.create({
  container: {
    // flex: 1,
    alignItems: 'flex-start',
    justifyContent: 'space-between',
    flexDirection: 'row',
    marginLeft: 20,
    marginBottom: 15,
    marginTop: 10,
    marginRight: 20,
    backgroundColor: '#dedede',
    borderRadius: 10,
    paddingBottom: 5,
    paddingTop: 5,
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

export default TicketItem;
