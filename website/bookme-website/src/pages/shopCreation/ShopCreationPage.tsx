import React, { useState, ChangeEvent } from 'react';
import firebase from 'firebase/app';
import 'firebase/storage';
import { Button, Form } from 'react-bootstrap';
import { useForm } from 'react-hook-form';
import './ShopCreationPage.css';
import axios from 'axios';
import { v4 as uuid } from 'uuid';
import { Redirect } from 'react-router-dom';
import { useAuth } from '../../contexts/AuthContext';
import config from '../../dev';

interface DataType {
  image: FileList;
  title: string;
  description: string;
  location: string;
  price: string;
}

function ShopCreationPage(): JSX.Element {
  const [imageFile, setImageFile] = useState('');
  const [hasLoaded, setHasLoaded] = useState(false);
  const { register, handleSubmit } = useForm();
  const { id, category } = useAuth();

  const onSubmit = (data: DataType) => {
    const { image, title, description, location, price } = data;
    const rawImage = image[0];
    const storage = firebase.storage();

    if (image[0].size > 5000000) {
      console.error('FILE SIZE IS BIGGER THAN THE LIMIT. ABORT!!!!!!');
    } else {
      const shopId = uuid();
      const uploadTask = storage.ref(rawImage.name).put(rawImage);
      uploadTask.on(
        'state_changed',
        (snapshot) => {
          console.log(snapshot);
        },
        (err) => {
          console.log(err);
        },
        () => {
          storage
            .ref(rawImage.name)
            .getDownloadURL()
            .then((firebaseUrl) => {
              axios.put(`${config.databaseURL}/shops/${category}/${id}.json`, {
                title,
                description,
                imageUrl: firebaseUrl,
                id,
                location,
                price,
                slots: {
                  shopId: {
                    available: true,
                    startTime: '09:00',
                    endTime: '10:00',
                  },
                },
              });

              setHasLoaded(true);
            });
        },
      );
    }
  };

  const fileHandler = (e: ChangeEvent<HTMLInputElement>) => {
    setImageFile(URL.createObjectURL(e.target.files![0]));
  };

  return (
    <div className="main">
      <h1>Create your shop</h1>
      <form onSubmit={handleSubmit(onSubmit)}>
        {imageFile !== '' ? <img src={imageFile} alt="Unknmown" /> : null}
        <Form.Group controlId="formFile" className="form">
          <Form.Label className="label">Shop photo (max 3MB)</Form.Label>
          <input type="file" className="mb-3" accept=".png,.jpg,.jpeg" {...register('image')} onChange={fileHandler} />
        </Form.Group>
        <Form.Group className="form">
          <Form.Label className="label">Shop title</Form.Label>
          <Form.Control type="text" placeholder="Enter shop title" {...register('title')} />
        </Form.Group>
        <Form.Group className="form">
          <Form.Label className="label">Description</Form.Label>
          <Form.Control
            as="textarea"
            rows={5}
            type="text"
            placeholder="Enter description"
            {...register('description')}
          />
        </Form.Group>
        <Form.Group className="form">
          <Form.Label className="label">Location</Form.Label>
          <Form.Control type="text" placeholder="Enter shop location" {...register('location')} />
        </Form.Group>
        <Form.Group className="form">
          <Form.Label className="label">Price</Form.Label>
          <Form.Select aria-label="Price" {...register('price')}>
            <option value="$">$</option>
            <option value="$$">$$</option>
            <option value="$$$">$$$</option>
          </Form.Select>
        </Form.Group>
        <Button className="submit-btn" type="submit">
          UPLOAD
        </Button>
      </form>
      {hasLoaded ? <Redirect to="/home" /> : null}
    </div>
  );
}

export default ShopCreationPage;
