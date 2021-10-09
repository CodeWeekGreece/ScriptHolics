import React, { useState, useEffect } from 'react';
import { useForm } from 'react-hook-form';
import { Link, Redirect } from 'react-router-dom';
import { Button, Form } from 'react-bootstrap';
import './LoginPage.css';
import Spacer from 'react-spacer';
import { useAuth } from '../../../contexts/AuthContext';

interface DataType {
  email: string;
  password: string;
}

function LoginPage(): JSX.Element {
  const { register, handleSubmit } = useForm();
  const [hasLoaded, setHasLoaded] = useState<boolean>(false);
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [error, setError] = useState('');
  const { login, result } = useAuth();

  useEffect(() => {
    if (result !== null) {
      if (result[0] === 0) {
        setIsLoading(false);
        setError(result[1]);
      } else {
        setIsLoading(false);
        setHasLoaded(true);
      }
    }
  }, [result]);

  const onSubmit = async (data: DataType) => {
    setIsLoading(true);
    login(data.email, data.password);
  };

  return (
    <div className="main">
      <h2>Welcome</h2>
      <form onSubmit={handleSubmit(onSubmit)}>
        <Form.Group className="form">
          <Form.Label className="label">Email address</Form.Label>
          <Form.Control type="email" placeholder="Enter email" {...register('email')} />
        </Form.Group>
        <Form.Group className="form password">
          <Form.Label className="label">Password</Form.Label>
          <Form.Control type="password" placeholder="Password" {...register('password')} />
        </Form.Group>
        <Button className="login-btn" variant="primary" type="submit" disabled={isLoading}>
          {isLoading ? 'Loading...' : 'Login'}
        </Button>
      </form>
      <p className="error-message">{error !== undefined ? error : ''}</p>
      <Spacer height="30px" />
      <p>
        Don&apos;t have an account? <Link to="/register">Register</Link>
      </p>
      {hasLoaded ? <Redirect to="/home" /> : null}
    </div>
  );
}

export default LoginPage;
