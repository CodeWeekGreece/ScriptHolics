import React from 'react';
import * as MdIcons from 'react-icons/md';

const SidebarData = [
  {
    title: 'Home',
    path: '/home',
    icon: <MdIcons.MdHome />,
    className: 'nav-text',
  },
  {
    title: 'Reservations',
    path: '/reservations',
    icon: <MdIcons.MdEvent />,
    className: 'nav-text',
  },
  {
    title: 'Settings',
    path: '/settings',
    icon: <MdIcons.MdSettings />,
    className: 'nav-text',
  },
];

export default SidebarData;
