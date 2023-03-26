import React, {Component} from 'react';
import axios from "axios";
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import {CardActionArea} from '@mui/material';

class DepthApi extends Component {

    state = {
        data: {
            id: null,
            pair: "aaa_bbb",
            ask: "0.0...",
            bid: "0.0...",
            date: "..."
        }
    }

    getDepth = () => {
        axios.get('http://localhost:8080/api/getDepth?pair=usd_rur')
            .then(({data}) => {
                console.log(data);
                this.setState({data: data})
            });
        return undefined;
    }

    render() {
        const {data} = this.state;
        console.log(data.date);
        return (
            <div>
                <Card sx={{maxWidth: 200}} onClick={() => this.getDepth()}>
                    <CardActionArea>
                        <CardContent>
                            <Typography gutterBottom variant="h5" component="div">{data.pair}</Typography>
                            <Typography variant="body2" color="text.secondary">Depth</Typography>
                            <Typography variant="body2" color="text.secondary">{data.ask}</Typography>
                            <Typography variant="body2" color="text.secondary">{data.bid}</Typography>
                            <Typography variant="body2" color="text.secondary">{data.date}</Typography>
                        </CardContent>
                    </CardActionArea>
                </Card>
            </div>
        );
    }
}

export default DepthApi;