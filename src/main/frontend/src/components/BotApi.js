import React, {Component} from 'react';
import axios from "axios";
import {Button} from "@mui/material";

class BotApi extends Component {

    state = {
        data: "...",
        loaded: false
    }

    getBot = () => {
        this.setState({loaded: true})
        axios.get('http://localhost:8080/api/getBot')
            .then(({data}) => {
                this.setState({data: data, loaded: false})
            });
        return undefined;
    }

    render() {
        const {data, loaded} = this.state;
        return (
            <div>
                <Button sx={{maxWidth: 200}} variant="contained" color="success" onClick={() => this.getBot()}
                        disabled={loaded}>
                    Start Bot
                </Button>
            </div>
        );
    }
}

export default BotApi;
