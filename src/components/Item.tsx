import React from 'react';
import { Image, Pressable, StyleSheet, Text, View } from 'react-native';
import { AirbnbRating } from 'react-native-ratings';
import { useNavigation } from '@react-navigation/core';


interface IProps {}

const Item: React.FC<IProps> = ({data}) => {
  const navigation = useNavigation();

  return (
    <Pressable
      android_ripple={{ color: '#c0c0c0' }}
      onPress={() => navigation.navigate('Reserve', data)}
    >
      <View style={styles.container}>
        <Image style={styles.image} source={{ uri: data.image }} />
        <View style={styles.infoContainer}>
          <Text style={styles.title}>
            {data.name}
          </Text>
          <AirbnbRating
            size={15}
            isDisabled
            defaultRating={data.review}
            starContainerStyle={{marginLeft: 1}}
            showRating={false}
          />
        </View>
      </View>
    </Pressable>
  )
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    marginBottom: 15,
    marginLeft: 5,
    marginRight: 5,
    flexDirection: 'row'
  },
  title: {
    marginLeft: 5,
    alignSelf: 'flex-start',
  },
  image: {
    width: 120,
    height: 100,
    resizeMode: 'cover',
  },
  infoContainer: {
    flexDirection: 'column',
    justifyContent: 'space-between',
    alignItems: 'flex-start'
  }
});

export default Item;
