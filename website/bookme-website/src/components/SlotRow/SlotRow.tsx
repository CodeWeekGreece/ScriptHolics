import React, { useState } from 'react';
import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  TableCell,
  TableRow,
  TextField,
} from '@material-ui/core';
import axios from 'axios';
import * as MdIcons from 'react-icons/md';
import { useAuth } from '../../contexts/AuthContext';
import { Slot } from '../../data/types';
import './SlotRow.css';
import firebaseConfig from '../../dev';

function SlotRow({ slot, slotNum }: IProps): JSX.Element {
  const [editing, setEditing] = useState<boolean>(false);
  const [start, setStart] = useState<string>(slot.startTime);
  const [end, setEnd] = useState<string>(slot.endTime);
  const [deleteOpen, setDeleteOpen] = useState<boolean>(false);
  const { id, category } = useAuth();

  const handleEdit = () => {
    if (!editing) {
      setEditing(true);
      return;
    }

    setEditing(false);
    axios.put(`${firebaseConfig.databaseURL}/shops/${category}/${id}/slots/${slot.uid}.json`, {
      available: true,
      startTime: start,
      endTime: end,
      uid: slot.uid,
    });
  };

  const deleteSlot = () => {
    setDeleteOpen(false);
    axios.delete(`${firebaseConfig.databaseURL}/shops/${category}/${id}/slots/${slot.uid}.json`);
  };

  return (
    <>
      <TableRow>
        <TableCell component="th" align="center">
          Slot {slotNum}
        </TableCell>
        <TableCell align="center">
          <TextField
            className="time"
            disabled={!editing}
            type="time"
            value={start}
            onChange={(e) => setStart(e.target.value)}
            inputProps={{
              step: 300, // 5 min
            }}
          />
        </TableCell>
        <TableCell align="center">
          <TextField
            className="time"
            disabled={!editing}
            type="time"
            value={end}
            onChange={(e) => setEnd(e.target.value)}
            inputProps={{
              step: 300, // 5 min
            }}
          />
        </TableCell>
        <TableCell align="center">{slot.available === true ? 'No' : 'Yes'}</TableCell>
        <TableCell align="center">
          <div onClick={handleEdit} onKeyDown={handleEdit} role="button" tabIndex={0} style={{ cursor: 'pointer' }}>
            {editing ? <MdIcons.MdDone size={18} /> : <MdIcons.MdEdit size={18} />}
          </div>
        </TableCell>
        <TableCell align="center">
          <MdIcons.MdClose size={24} onClick={() => setDeleteOpen(true)} style={{ cursor: 'pointer' }} />
        </TableCell>
      </TableRow>
      <Dialog open={deleteOpen}>
        <DialogTitle>Delete slot?</DialogTitle>
        <DialogContent>
          <DialogContentText>Slot will be permanently deleted. Are you sure?</DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setDeleteOpen(false)} color="primary">
            No
          </Button>
          <Button onClick={deleteSlot} color="primary" autoFocus>
            Yes
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
}

type IProps = {
  slot: Slot;
  slotNum: number;
};

export default SlotRow;
