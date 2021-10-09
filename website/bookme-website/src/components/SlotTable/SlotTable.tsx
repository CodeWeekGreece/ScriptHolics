import React from 'react';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper } from '@material-ui/core';
import { Slot } from '../../data/types';
import SlotRow from '../SlotRow/SlotRow';
import './SlotTable.css';

function SlotTable({ slots }: IProps): JSX.Element {
  return (
    <TableContainer className="table" component={Paper}>
      <Table aria-label="simple table">
        <TableHead>
          <TableRow>
            <TableCell align="center">Slot</TableCell>
            <TableCell align="center">Starts at</TableCell>
            <TableCell align="center">Ends at</TableCell>
            <TableCell align="center">Reserved</TableCell>
            <TableCell align="center">Edit</TableCell>
            <TableCell align="center">Delete</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {slots.map((slot, i) => (
            <SlotRow slot={slot} slotNum={i + 1} key={slot.uid} />
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}

type IProps = {
  slots: Array<Slot>;
};

export default SlotTable;
