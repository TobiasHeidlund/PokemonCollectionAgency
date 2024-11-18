import {PoedexPokemon, PokemonSpecies, Versions} from "../Models.ts";
import {useEffect, useState} from "react";
import axios from "axios";
import {Accordion, AccordionDetails, AccordionSummary, Dialog, Typography} from "@mui/material";
import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemAvatar from '@mui/material/ListItemAvatar';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import DialogTitle from '@mui/material/DialogTitle';
import PersonIcon from '@mui/icons-material/Person';
import { blue } from '@mui/material/colors';
import "./Pokedex.css"

interface Props {
    version: Versions|undefined;
    update: number;
    setUpdate: ()=>{};
}
export interface SimpleDialogProps {
    open: boolean;
    selectedValue: number;
    onClose: (value: number[]) => void;
    selectableValues?: PoedexPokemon;

}

function SimpleDialog(props: SimpleDialogProps) {
    const { onClose, open, selectableValues } = props;

    const handleClose = () => {
        onClose([]);
    };

    const handleListItemClick = (value: number) => {
        if (selectableValues) {
            onClose([selectableValues.id, value]); // Ensure selectableValues exists before accessing
        }
    };
    return (
        <Dialog onClose={handleClose} open={open}>
            <DialogTitle>Set backup account</DialogTitle>
            <List sx={{ pt: 0 }}>
                {selectableValues?.canBeEvolvedTo && selectableValues.canBeEvolvedTo.length > 0 ? (
                    selectableValues.canBeEvolvedTo.map((item) => (
                    <ListItem disableGutters key={item.species.id}>
                        <ListItemButton onClick={() => handleListItemClick(item.species.id)}>
                            <ListItemAvatar>
                                <Avatar sx={{ bgcolor: blue[100], color: blue[600] }}>
                                    <PersonIcon />
                                </Avatar>
                            </ListItemAvatar>
                            <ListItemText primary={item.species.identifier} />
                        </ListItemButton>
                    </ListItem>
                ))):(<p>not avail</p>)}
            </List>
        </Dialog>
    );
}

export default function Pokedex({version, update, setUpdate}: Props) {
    const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;
    let [toCollect, settoCollect] = useState<PoedexPokemon[]>([]);
    const [open, setOpen] = useState(false);
    const [currentEvoluions, setCurrentEvoluions] = useState<PoedexPokemon>();

    useEffect(() => {
        settoCollect([])
        if (version) {
            axios.get(`${API_BASE_URL}/pokedex/collected?version=` + version.id).then((resp) => {
                console.log(resp.data)
                let col = resp.data as PoedexPokemon[]
                console.log(col)
                settoCollect(col)
            })
        }
    }, [version,update]);


    const evolve:React.MouseEventHandler<HTMLButtonElement> = (it)=>{
        it.preventDefault();
        let id = parseInt(it.currentTarget.id, 10);
        setCurrentEvoluions(toCollect.find(it => it.id===id))


    }
    useEffect(() => {
        if(currentEvoluions){
            setOpen(true);
        }

    }, [currentEvoluions]);


    const handleClose = (value: number[]) => {
        setOpen(false);
        setCurrentEvoluions(undefined)
        if (value.length != 0){
            axios.put(`${API_BASE_URL}/pokedex/evolve?pokedexid=` + value[0] + "&tospecies="+value[1]).then((resp) => {
                console.log(resp.status)
            })
        }
        setUpdate(it => it+1)
    };
    const [expanded, setExpanded] = React.useState<string | false>('panel1');
    const handleChange =
        (panel: string) => (event: React.SyntheticEvent, newExpanded: boolean) => {
            setExpanded(newExpanded ? panel : false);
        };


    return(<section>
        <SimpleDialog
            open={open}
            onClose={handleClose}
            selectableValues={currentEvoluions}
            selectedValue={0}/>
        <Accordion className="accord" expanded={expanded === 'panel1'} onChange={handleChange('panel1')}>
        <AccordionSummary className="sum" aria-controls="panel1d-content" id="panel1d-header">
            <p className="typ">Currently Collected</p>
        </AccordionSummary>
        <AccordionDetails>
        {toCollect.map((it) => (
            <div className="PokedexItem">
                <p>{it.species.identifier}</p>
                <p className="num">{it.isCollected}</p>
                <button className="evolve" id={""+it.id} onClick={evolve}>Evolve</button>
            </div>
            )
        )}
        </AccordionDetails>
        </Accordion>
    </section>)
}