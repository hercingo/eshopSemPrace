/*import React, { Component } from 'react';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';
import { Button, Container } from 'reactstrap';
import { Alert } from "react-bootstrap"

import AuthenticationService from '../services/AuthenticationService';

class Profile extends Component {

    constructor(props) {
        super(props);
        this.state = {user: undefined};
    }

    componentDidMount() {
        const user = AuthenticationService.getCurrentUser();
        this.setState({user: user});
    }

    render() {
        let userInfo = "";
        const user = this.state.user;

        // login
        if (user && user.accessToken) {

            let roles = "";

            user.authorities.forEach(authority => {
                roles = roles + " " + authority.authority
            });

            userInfo = (
                <div style={{marginTop:"20px"}}>
                    <Alert variant="info">
                        <h2>User Info</h2>
                        <ul>
                            <li>Username: {user.username}</li>
                            <li>Access Token: {user.accessToken}</li>
                            <li>Authorities: {roles}</li>
                        </ul>
                    </Alert>
                </div>
            );
        } else { // not login
            userInfo = <div style={{marginTop:"20px"}}>
                <Alert variant="primary">
                    <h2>Profile Component</h2>
                    <Button color="success"><Link to="/signin"><span style={{color:"white"}}>Login</span></Link></Button>
                </Alert>
            </div>
        }

        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    {userInfo}
                </Container>
            </div>
        );
    }
}

export default Profile;*/
import React, {useEffect, useState} from 'react'
import {Link} from 'react-router-dom'
import {Alert, Button, Container} from 'reactstrap'

import AuthenticationService from '../services/AuthenticationService'
import AppNavbar from "./AppNavbar";

function Profile() {
    const [user, setUser] = useState(undefined)

    useEffect(() => {
        const user = AuthenticationService.getCurrentUser();
        setUser(user);
    }, [])

    let userInfo;

    if (user && user.accessToken) {
        let roles = "";
        user.authorities.forEach(authority => {
            roles = roles + " " + authority.authority
        });

        userInfo = (
            <div style={{marginTop: "20px"}}>
                <Alert variant="info">
                    <h2>Dobry den</h2>
                    <ul>
                        <li>Vitejte pane/pani: {user.username}</li>
                    </ul>
                </Alert>
            </div>
        );
    }

    return (
        <div>
            <AppNavbar/>
            <Container fluid>
                {userInfo}
            </Container>
        </div>
    )
}

export default Profile;
