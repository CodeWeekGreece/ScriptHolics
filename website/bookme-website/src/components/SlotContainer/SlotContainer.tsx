import React, { useEffect } from 'react';

import { Owner, Slot } from '../../data/types';
import './SlotContainer.css';

function SlotContainer({ slot, owner }: IProps): JSX.Element {
  return (
    <div className="container">
      <p>
        {owner.name} - {owner.phone}
      </p>
      <p>
        {slot.startTime}-{slot.endTime}
      </p>
    </div>
  );
}

type IProps = {
  slot: Slot;
  owner: Owner;
};

export default SlotContainer;
