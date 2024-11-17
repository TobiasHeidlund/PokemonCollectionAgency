import {Region, Versions,toCollect} from "../Models.ts";
import {useEffect, useState} from "react";
import axios from "axios";
import {Accordion, AccordionDetails, AccordionSummary, Typography} from "@mui/material";
import * as React from "react";

interface Props {
    version: Versions|undefined;
    update: number;
}



export default function ToCollect({version,update }: Props) {
    let [toCollect, settoCollect] = useState<toCollect[]>([]);
    useEffect(() => {
        if (version) {
            axios.get("http://localhost:8080/pokedex/tocollect?version=" + version.id).then((resp) => {
                console.log(resp.data)
                let col = resp.data as toCollect[]
                console.log(col)
                settoCollect(col)
            })
        }
    }, [version,update]);
    const [expanded, setExpanded] = React.useState<string | false>('panel1');
    const handleChange =
        (panel: string) => (event: React.SyntheticEvent, newExpanded: boolean) => {
            setExpanded(newExpanded ? panel : false);
        };
    return(
        <Accordion className="accord" expanded={expanded === 'panel1'} onChange={handleChange('panel1')}>
            <AccordionSummary classname="sum" aria-controls="panel1d-content" id="panel1d-header">
                <p className="typ">To Collected</p>
            </AccordionSummary>
            <AccordionDetails>
        <div className="toCollect">
            {toCollect.map(it=> (<p title={ it.encounters.join("\n") }>{it.pokemon.identifier}</p>))}
        </div>
            </AccordionDetails>
        </Accordion>

    )


}