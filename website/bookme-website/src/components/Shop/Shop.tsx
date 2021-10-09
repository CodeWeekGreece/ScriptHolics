import React, { useState, useEffect } from 'react';
import {
  CircularProgress,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogContentText,
  DialogActions,
} from '@material-ui/core';
import axios from 'axios';
import { v4 as uuid } from 'uuid';
import firebase from 'firebase/app';
import { FirebaseDatabaseNode, FirebaseDatabaseProvider } from '@react-firebase/database';
import { Button, FormControl, InputGroup } from 'react-bootstrap';
import { ShopType, Slot } from '../../data/types';
import { useAuth } from '../../contexts/AuthContext';
import firebaseConfig from '../../dev';
import './Shop.css';
import SlotTable from '../SlotTable/SlotTable';

function Shop({ shop }: IProps): JSX.Element {
  const [hasLoaded, setHasLoaded] = useState<boolean>(false);
  const [description, setDescription] = useState<string>(shop.description);
  const [defaultDescription, setDefaultDescription] = useState<string>(shop.description);
  const [editing, setEditing] = useState<boolean>(false);
  const [cancelOpen, setCancelOpen] = useState<boolean>(false);
  const [saveOpen, setSaveOpen] = useState<boolean>(false);
  const { title, imageUrl, slots } = shop;
  const { id, category } = useAuth();
  const [slotsArray, setSlotsArray] = useState<Array<Slot>>([]);
  // const [slotNum, setSlotNum] = useState<string>(slotsArray.length.toString());
  // const [parentSlotNum, setParentSlotNum] = useState<string>(slotNum);
  // const [editingSlots, setEditingSlots] = useState<boolean>(false);

  useEffect(() => {
    if (slots != null) {
      setSlotsArray(Object.keys(slots).map((index) => slots[index as keyof typeof slots]) as unknown as Slot[]);
    }
  }, [slots]);

  const handleEdit = () => {
    if (!editing) {
      setEditing(true);
    } else {
      setSaveOpen(true);
    }
  };

  const cancelEdit = () => {
    setDescription(defaultDescription);
    setCancelOpen(false);
    setEditing(false);
  };

  const saveEdit = () => {
    setDefaultDescription(description);
    setEditing(false);
    setSaveOpen(false);
    axios.patch(`${firebaseConfig.databaseURL}/shops/${category}/${id}.json`, {
      description,
    });
  };

  const addSlot = () => {
    const newSlot = { available: true, endTime: '00:00', owner: null, startTime: '00:00', uid: uuid() };

    setSlotsArray([...slotsArray, newSlot]);
  };

  return (
    <div>
      <h1>{title}</h1>
      <div className="image-description">
        <CircularProgress style={{ display: hasLoaded ? 'none' : 'inherit' }} />
        <img
          onLoad={() => setHasLoaded(true)}
          className="image"
          style={{ display: hasLoaded ? 'inherit' : 'none' }}
          src={imageUrl}
          alt="Shop"
          width="500"
          height="300"
        />
        <InputGroup className="description-container">
          <FormControl
            onChange={(e) => setDescription(e.target.value)}
            disabled={!editing}
            className="description"
            value={description}
            as="textarea"
            aria-label="With textarea"
          />
        </InputGroup>
        <div className="edit-buttons">
          <Button type="button" onClick={handleEdit}>
            {editing ? 'Save' : 'Edit'}
          </Button>
          <Button
            onClick={() => setCancelOpen(true)}
            variant="secondary"
            style={{ display: editing ? 'block' : 'none' }}
            type="button"
          >
            Cancel
          </Button>
        </div>
        <div className="slot-container">
          <h4>Slots:</h4>
        </div>
        <FirebaseDatabaseProvider {...firebaseConfig} firebase={firebase}>
          <FirebaseDatabaseNode path={`/shops/${category}/${id}/slots`}>
            {(d) => {
              if (d.value !== null) {
                const slotArray = Object.keys(d.value).map((key) => d.value[key]);
                setSlotsArray(slotArray);
              }

              return null;
            }}
          </FirebaseDatabaseNode>
        </FirebaseDatabaseProvider>
        <SlotTable slots={slotsArray} />
        <Button className="add-button" variant="light" onClick={addSlot}>
          Add Slot
        </Button>
      </div>
      <div id="dialogs">
        <Dialog open={cancelOpen}>
          <DialogTitle>Cancel edit?</DialogTitle>
          <DialogContent>
            <DialogContentText>Progress will be lost. Are you sure you want to cancel?</DialogContentText>
          </DialogContent>
          <DialogActions>
            <Button onClick={() => setCancelOpen(false)} color="primary">
              No
            </Button>
            <Button onClick={cancelEdit} color="primary" autoFocus>
              Yes
            </Button>
          </DialogActions>
        </Dialog>

        <Dialog open={saveOpen}>
          <DialogTitle>Save new description?</DialogTitle>
          <DialogContent>
            <DialogContentText>New description will be saved. Proceed?</DialogContentText>
          </DialogContent>
          <DialogActions>
            <Button onClick={() => setSaveOpen(false)} color="primary">
              No
            </Button>
            <Button onClick={saveEdit} color="primary" autoFocus>
              Yes
            </Button>
          </DialogActions>
        </Dialog>
      </div>
    </div>
  );
}

interface IProps {
  shop: ShopType;
}

export default Shop;
