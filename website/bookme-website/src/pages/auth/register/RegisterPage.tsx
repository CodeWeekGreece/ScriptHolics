import React, { useState, useEffect } from 'react';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import { useForm } from 'react-hook-form';
import { Redirect } from 'react-router-dom';
import Spacer from 'react-spacer';
import { useAuth } from '../../../contexts/AuthContext';
import './RegisterPage.css';

interface DataType {
  email: string;
  password: string;
  phone: string;
  cat: string;
}

function RegisterPage(): JSX.Element {
  const [isLoading, setIsLoading] = useState(false);
  const [hasLoaded, setHasLoaded] = useState(false);
  const { register, handleSubmit } = useForm();
  const { registerUser, result } = useAuth();

  const onSubmit = async (data: DataType) => {
    setIsLoading(true);
    registerUser(data.email, data.password, data.phone, data.cat);
  };

  useEffect(() => {
    if (result !== null) {
      if (result[0] === 0) {
        setIsLoading(false);
      } else {
        setIsLoading(false);
        setHasLoaded(true);
      }
    }
  }, [result]);

  return (
    <div className="main">
      <h2>Register</h2>
      <form className="register-form" onSubmit={handleSubmit(onSubmit)}>
        <div className="row">
          <div className="column">
            <Form.Group className="form">
              <Form.Label className="label">Email address</Form.Label>
              <Form.Control type="email" placeholder="Enter email" {...register('email')} />
            </Form.Group>
            <Form.Group className="form">
              <Form.Label className="label">Password</Form.Label>
              <Form.Control type="password" placeholder="Password" {...register('password')} />
              <Spacer height={10} />
              <Form.Control type="password" placeholder="Confirm Password" />
            </Form.Group>
          </div>
          <Spacer width={16} />
          <div className="column">
            <Form.Group className="form">
              <Form.Label className="label">Phone number</Form.Label>
              <Form.Control type="" placeholder="Phone number" {...register('phone')} />
            </Form.Group>
            <Form.Group className="form">
              <Form.Label className="label">Shop category</Form.Label>
              <Form.Select {...register('cat')}>
                <option value="restaurants">Restaurant</option>
                <option value="clothing">Clothing</option>
                <option value="selfcare">Self Care</option>
                <option value="supermarkets">Supermarket</option>
              </Form.Select>
            </Form.Group>
          </div>
        </div>
        <Spacer height={20} />
        <Button variant="primary" type="submit" disabled={isLoading}>
          {isLoading ? 'Loading...' : 'Register'}
        </Button>
      </form>
      {hasLoaded ? <Redirect to="/shopCreation" /> : null}
    </div>
  );
}

export default RegisterPage;
