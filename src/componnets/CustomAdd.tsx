import AddIcon from "@mui/icons-material/Add";
import {Button, Dialog, DialogActions, DialogContent, DialogContentText, TextField} from "@mui/material";
import DialogTitle from "@mui/material/DialogTitle";
import React, {Fragment, useState} from "react";
import axios from "axios";
import {Versions} from "../Models.ts";



interface Props {
    setversion: ()=>void;
}

export default function CustomAdd({setversion}: Props) {

    const [open, setOpen] = useState(false);

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    return (
        <Fragment>
            <AddIcon variant="outlined" onClick={handleClickOpen}>
                Open form dialog
            </AddIcon>
            <Dialog
                open={open}
                onClose={handleClose}
                PaperProps={{
                    component: 'form',
                    onSubmit: (event: React.FormEvent<HTMLFormElement>) => {
                        event.preventDefault();
                        const formData = new FormData(event.currentTarget);
                        const formJson = Object.fromEntries((formData as any).entries());
                        const name = formJson.name;
                        axios.put("http://localhost:8080/pokedex/identifier?identifier="+name).then(
                            (resp) => {
                                if(resp.status != 200){
                                    alert("Failed To add")
                                    console.log(resp.data)
                                }else{
                                    setversion()
                                }
                            })
                        handleClose();
                    },
                }}
            >
                <DialogTitle>Add Pokemon</DialogTitle>
                <DialogContent>
                    <TextField
                        autoFocus
                        required
                        margin="dense"
                        id="name"
                        name="name"
                        label="Pokemon Name"
                        type="text"
                        fullWidth
                        variant="standard"
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>Cancel</Button>
                    <Button type="submit">Add</Button>
                </DialogActions>
            </Dialog>
        </Fragment>
    );
}