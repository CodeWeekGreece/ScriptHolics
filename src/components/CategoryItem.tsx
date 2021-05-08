import { useNavigation } from '@react-navigation/core';
import React from 'react';
import { StyleSheet, Text, View, Image, ImageBackground, Pressable } from 'react-native';

interface IProps {}

const CategoryItem: React.FC<IProps> = ({data}) => {
  const navigation = useNavigation();
  
  return (
    <View style={styles.container}>
      <Pressable onPress={() => navigation.navigate(data.title.replace(/\s/g, ''))}>
        <ImageBackground 
          imageStyle={{borderRadius: 25}}
          source={{ uri: data.image }} 
          style={styles.image}
        >
          <View style={{marginBottom: 20, backgroundColor: 'rgba(52, 52, 52, 0.4)'}}>
            <Text style={styles.text}>{data.title}</Text>
          </View>
        </ImageBackground>
      </Pressable>
    </View>
  )
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  image: {
    marginBottom: 10,
    width: 300,
    height: 150,
    resizeMode: 'cover',
    flex: 1,
    alignItems: 'center',
    justifyContent: 'flex-end',
  },
  text: {
    color: 'white',
    fontWeight: 'bold',
    fontSize: 30,
    // marginBottom: 20,
  }
});

export default CategoryItem;
