import React, {Component} from 'react';
import axios from "axios";
import {Button, Card, CardActionArea, CardContent, Typography} from "@mui/material";

class InfoApi extends Component {

    state = {
        data: "pair size = ?",
        loaded: false
    }

    getInfo = () => {
        axios.get('http://localhost:8080/api/getInfo')
            .then(({data}) => {
                this.setState({data: data})
            });
        return undefined;
    }

    updateInfo = () => {
        this.setState({loaded: true})
        axios.get('http://localhost:8080/api/updateInfo')
            .then(({data}) => {
                this.setState({data: data, loaded: false})
            });
        return undefined;
    }

    render() {
        const {data, loaded} = this.state;

        return (
            <div>
                <Card sx={{maxWidth: 200}} onClick={() => this.getInfo()}>
                    <CardActionArea>
                        <CardContent>
                            <Typography variant="body2" color="text.secondary">Info</Typography>
                            <Typography variant="body2" color="text.secondary">{data}</Typography>
                        </CardContent>
                    </CardActionArea>
                </Card>
                <Button sx={{maxWidth: 200}} variant="outlined" color="error" onClick={() => this.updateInfo()}
                        disabled={loaded}>
                    ! Update Info DB !
                </Button>
            </div>
        );
    }
}

export default InfoApi;