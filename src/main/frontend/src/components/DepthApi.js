import React, {Component} from 'react';
import axios from "axios";
import {Button, Card, CardActionArea, CardActions, CardContent, Typography} from "@mui/material";
import Moment from 'moment';

class DepthApi extends Component {

    state = {
        data: {
            id: null,
            pair: "aaa_bbb",
            ask: "0.0...",
            bid: "0.0...",
            date: ""
        },
        loaded: false
    }

    getDepth = () => {
        axios.get('http://localhost:8080/api/getDepth?pair=usd_rur')
            .then(({data}) => {
                this.setState({data: data})
            });
        return undefined;
    }

    updateDepth = () => {
        this.setState({loaded: true})
        axios.get('http://localhost:8080/api/updateDepth?pair=usd_rur')
            .then(({data}) => {
                this.setState({data: data, loaded: false})
            });
        return undefined;
    }

    render() {
        const {data, loaded} = this.state;
        const date = data.date ? Moment(data.date).format('hh:mm:ss D-MMM-YY') : '...'

        return (
            <div>
                <Card sx={{maxWidth: 200}} onClick={() => this.getDepth()}>
                    <CardActionArea>
                        <CardContent>
                            <Typography gutterBottom variant="h5" component="div">{data.pair}</Typography>
                            <Typography variant="body2" color="text.secondary">Depth</Typography>
                            <Typography variant="body2" color="text.secondary">{data.ask}</Typography>
                            <Typography variant="body2" color="text.secondary">{data.bid}</Typography>
                            <Typography variant="body2" color="text.secondary">{date}</Typography>
                        </CardContent>
                    </CardActionArea>
                </Card>
                <Button sx={{maxWidth: 200}} variant="outlined" color="error" onClick={() => this.updateDepth()}
                        disabled={loaded}>
                    ! Update Depth DB !
                </Button>
            </div>
        );
    }
}

export default DepthApi;