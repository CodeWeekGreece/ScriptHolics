import { User } from '../data/types';

export const loadFromStorage = (key: string): User | undefined => {
  try {
    const serializedState = localStorage.getItem(key);
    if (serializedState === null) return undefined;
    return JSON.parse(serializedState);
  } catch (e) {
    return undefined;
  }
};

export const saveToStorage = (key: string, userToSave: User): void => {
  const serializedState = JSON.stringify(userToSave);
  localStorage.setItem(key, serializedState);
};

export const deleteFromStorage = (key: string): void => {
  localStorage.removeItem(key);
};
