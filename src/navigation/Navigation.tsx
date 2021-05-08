import React, { useState } from 'react';
import { createStackNavigator } from '@react-navigation/stack';
import { View, Text, BackHandler, ToastAndroid } from 'react-native';
import { getFocusedRouteNameFromRoute, useNavigation, useRoute } from '@react-navigation/native'
import UserHome from '../screens/user/UserHome';
import { MaterialIcons, MaterialCommunityIcons } from '@expo/vector-icons';
import { createMaterialBottomTabNavigator } from '@react-navigation/material-bottom-tabs';
import MyResevations from '../screens/user/MyResevations';
import Login from '../screens/accounts/Login';
import ManagerHome from '../screens/manager/ManagerHome';
import UserSettings from '../screens/user/UserSettings';
import ManagerSettings from '../screens/manager/ManagerSettings';
import Restaurants from '../screens/categories/Restaurants';
import { createSwitchNavigator } from '@react-navigation/compat';
import Supermarkets from '../screens/categories/Supermarkets';
import SelfCare from '../screens/categories/SelfCare';
import Clothing from '../screens/categories/Clothing';
import Reserve from '../screens/user/Reserve';
import UserLogin from '../screens/accounts/UserLogin';
import ManagerLogin from '../screens/accounts/ManagerLogin';
import ManageReservations from '../screens/manager/ManageReservations';
import Filter from '../screens/categories/Filter';
import UserSignup from '../screens/accounts/UserSignup';
import ManagerSignup from '../screens/accounts/ManagerSignup';

const Tab = createMaterialBottomTabNavigator();

type TabBarProps = {
  color: string,
}

const UserTabs = () => {
  const route = useRoute();
  const navigation = useNavigation();

  return(
    <Tab.Navigator barStyle={{backgroundColor: 'white'}} initialRouteName="Shows" backBehavior="initialRoute">
      <Tab.Screen
        name="Home"
        component={UserHome}
        options={{
          tabBarIcon: ({ color }: TabBarProps) => <MaterialIcons name="home" color={color} size={24} />,
        }}
      />
      <Tab.Screen
        name="Reservations"
        component={MyResevations}
        options={{
          tabBarIcon: ({ color }: TabBarProps) => <MaterialIcons name="event" color={color} size={24} />,
        }}
      />
      <Tab.Screen
        name="Settings"
        component={UserSettings}
        options={{
          tabBarIcon: ({ color }: TabBarProps) => <MaterialIcons name="settings" color={color} size={22} />,
        }}
      />
    </Tab.Navigator>
)};

const ManagerTabs = () => (
    <Tab.Navigator barStyle={{backgroundColor: 'white'}} initialRouteName="Shows" backBehavior="initialRoute">
      <Tab.Screen
        name="My Shop"
        component={ManagerHome}
        options={{
          tabBarIcon: ({ color }: TabBarProps) => <MaterialCommunityIcons name="shopping" color={color} size={22} />,
        }}
      />
      <Tab.Screen
        name="Reservations"
        component={ManageReservations}
        options={{
          tabBarIcon: ({ color }: TabBarProps) => <MaterialCommunityIcons name="ticket-account" color={color} size={22} />
        }}
      />
      <Tab.Screen
        name="Settings"
        component={ManagerSettings}
        options={{
          tabBarIcon: ({ color }: TabBarProps) => <MaterialIcons name="settings" color={color} size={22} />,
        }}
      />
    </Tab.Navigator>
);

const Stack = createStackNavigator();

const StackNav = () => {
  return (
    <Stack.Navigator initialRouteName="Login" mode="modal">
      <Stack.Screen name="Login" component={Login} />
      <Stack.Screen name="User Login" component={UserLogin} />
      <Stack.Screen name="Manager Login" component={ManagerLogin} />
      <Stack.Screen name="User Signup" component={UserSignup} />
      <Stack.Screen name="Manager Signup" component={ManagerSignup} />
      <Stack.Screen
        name="UserTabs"
        component={UserTabs}
        options={{
          headerLeft: null,
          headerTitle: 'BookMe'
        }}
      />
      <Stack.Screen
        name="ManagerTabs"
        component={ManagerTabs}
        options={{
          headerLeft: null,
          headerTitle: 'BookMe'
        }}
      />
      <Stack.Screen name="Restaurants" component={Restaurants} />
      <Stack.Screen name="Supermarkets" component={Supermarkets} />
      <Stack.Screen name="SelfCare" component={SelfCare} />
      <Stack.Screen name="Clothing" component={Clothing} />
      <Stack.Screen name="Reserve" component={Reserve} />
      <Stack.Screen name="Filter" component={Filter} />
    </Stack.Navigator>
  )
};

export default StackNav;