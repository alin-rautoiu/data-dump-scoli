$(document).ready(function() {

    $.get("/getJudete", drawMap);

})

function drawMap(judete) {
    console.log(judete);
    const data = JSON.parse(judete);
    const columns = Object.keys(data[0]);
    console.log(columns);
    const svg = d3.select('svg');
    const g = svg.append('g');
    const rect = g.selectAll('rect')
        .data(data)
        .enter()
        .append('rect')
            .attr('y', 0)
            .attr('height', (d) => {
                const column = columns[0];
                return d[column];
                })
            .attr('width', 20)
            .attr('x', (d, i) => i * 20 + 5)
}