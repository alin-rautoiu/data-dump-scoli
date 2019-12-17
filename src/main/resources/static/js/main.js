let data = [];

$(document).ready(function() {

    $.get("/getJudete", (judete) => {
        data = JSON.parse(judete);
        drawMap()
    });
    $('#refresh').on('click', () => {
        drawMap()
    })
})

function drawMap() {

    const columns = Object.keys(data[0]);
    const svg = d3.select('svg');
    svg.selectAll("*").remove();
    const margin = {
        top: 20,
        right: 20,
        bottom: 30,
        left: 50
    };
    const width = +svg.attr("width") - margin.left - margin.right;
    const height = +svg.attr("height") - margin.top - margin.bottom;

    const x = d3.scaleBand()
    	.rangeRound([0, width])
    	.padding(0.1);
    const y = d3.scaleLinear()
    	.rangeRound([height, 0])

    x.domain(data.map(function (d, i) {
                return d['judet'];
            }));
    y.domain([0, d3.max(data, function (d) {
                const column = $('#select').val();
                return d[column];
            })]);

    const g = svg
        .append('g')
        .attr("transform", "translate(" + margin.left + "," + margin.top + ")")

    g.append('g')
        .attr("transform", "translate(0," + height + ")")
        .call(d3.axisBottom(x));

    g.append("g")
        .call(d3.axisLeft(y))
        .append("text")
        .attr("fill", "#000")
        .attr("transform", "rotate(-90)")
        .attr("y", 6)
        .attr("dy", "0.71em")
        .attr("text-anchor", "end")
        .text($('#select').val());

    const rect = g.selectAll('.bar')
        .data(data)
        .enter()
        .append('rect')
            .attr('y', (d) => {
                const column = $('#select').val();
                return y(d[column]);
            })
            .attr('height', (d) => {
                const column = $('#select').val();
                return height - y(d[column]);
                })
            .attr('width', x.bandwidth())
            .attr('x', (d, i) => x(d['judet']))
            .attr('fill', 'steelblue')
            .attr("class", "bar")
}