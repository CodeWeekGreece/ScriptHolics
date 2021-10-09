import React from 'react';
import { useHistory } from 'react-router-dom';
import { IconContext } from 'react-icons';
import { Drawer, makeStyles } from '@material-ui/core';
import './NavBar.css';
import SidebarData from './SidebarData';

function NavBar(): JSX.Element {
  const classes = useStyles();
  const history = useHistory();

  const handleClick = (path: string) => {
    history.push(path);
  };

  return (
    <>
      <IconContext.Provider value={{ color: '#fff' }}>
        <Drawer
          variant="permanent"
          classes={{
            paper: classes.drawerPaper,
          }}
        >
          <nav className="nav-menu">
            <ul className="nav-menu-items">
              {SidebarData.map((item) => (
                <li className={item.className} key={item.path}>
                  <button
                    type="button"
                    tabIndex={0}
                    onClick={() => {
                      handleClick(item.path);
                    }}
                  >
                    {item.icon}
                    <span>{item.title}</span>
                  </button>
                </li>
              ))}
            </ul>
          </nav>
        </Drawer>
      </IconContext.Provider>
    </>
  );
}

const useStyles = makeStyles((theme) => ({
  drawerPaper: {
    position: 'relative',
    whiteSpace: 'nowrap',
    width: 200,
    paddingTop: theme.spacing(4),
    paddingBottom: theme.spacing(4),
    background: '#060b26',
    color: '#fff',
  },
}));

export default NavBar;
